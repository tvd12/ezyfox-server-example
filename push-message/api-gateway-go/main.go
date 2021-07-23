package main

import (
	"context"
    "encoding/json"
	"fmt"
	"github.com/segmentio/kafka-go"
	"io/ioutil"
    "log"
    "net/http"
)

var ctx context.Context
var kafkaPoducer kafka.Writer

type PostData struct {
	username string `json:"username"`
	data  map[string]string `json:"data"`
}

const (
	topic          = "message"
	kafkaBrokerAddress = "localhost:9092"
)

func handler(w http.ResponseWriter, r *http.Request) {
	postBytes, err := ioutil.ReadAll(r.Body)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		w.Write([]byte("can not read body"))
		return
	}
	defer r.Body.Close()

	payload := &PostData{}
	err = json.Unmarshal(postBytes, &payload)
	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		w.Write([]byte("can not unmarshal body"))
		return
	}
	fmt.Println("request body: ", string(postBytes))

	err = kafkaPoducer.WriteMessages(ctx, kafka.Message {
		Key: []byte("push"),
		Value: postBytes,
		Headers: []kafka.Header{kafka.Header{Key: "c", Value: []byte("json")}},
	})
	if err != nil {
		panic("could not write message " + err.Error())
	}
	fmt.Println("Write message to kafka successfully")

	w.WriteHeader(http.StatusOK)
	w.Write([]byte("success"))
	return
}

func main() {
	ctx = context.Background()
	kafkaPoducer = *kafka.NewWriter(kafka.WriterConfig{
		Brokers: []string{kafkaBrokerAddress},
		Topic:   topic,
	})
	http.HandleFunc("/api/v1/message/push", handler)
	fmt.Println("Listening on port 8082")
	log.Fatal(http.ListenAndServe(":8082", nil))
}