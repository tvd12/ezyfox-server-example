import logging
import sys
import json
from http.server import HTTPServer, BaseHTTPRequestHandler
from kafka import KafkaProducer

logger = logging.getLogger()
logger.setLevel(logging.INFO)
formatter = logging.Formatter('%(asctime)s | %(levelname)s | %(message)s', 
                              '%m-%d-%Y %H:%M:%S')

stdout_handler = logging.StreamHandler(sys.stdout)
stdout_handler.setLevel(logging.DEBUG)
stdout_handler.setFormatter(formatter)

logger.addHandler(stdout_handler)

kafkaProducer = KafkaProducer(bootstrap_servers='localhost:9092')

class ApiServer(BaseHTTPRequestHandler):

    def do_GET(self):
        if self.path == '/api/v1/message/push':
            self.methodNotAllow()
        else:
            self.notFound()
    
    def do_POST(self):
        if self.path == '/api/v1/message/push':
            self.messagePush()
        else:
            self.notFound()
            
    def messagePush(self):
        content_length = int(self.headers['Content-Length'])
        message = self.rfile.read(content_length)
        kafkaProducer.send('message', key=b'push', value = message, headers=[('c', b'json')])
        logger.info('push message: ' + message.decode("utf-8") )
        self.send_response(200)
        self.end_headers()
        self.wfile.write(b'true')

    def notFound(self):
        self.send_response(404)
        self.end_headers()
        self.wfile.write()

    def methodNotAllow(self):
        self.send_response(405)
        self.end_headers()
        self.wfile.write()

logger.info('server listening on 8083')
httpd = HTTPServer(('localhost', 8083), ApiServer)
httpd.serve_forever()