using System;
using System.Collections.Generic;
using com.tvd12.ezyfoxserver.client.binding;
using com.tvd12.ezyfoxserver.client.entity;


namespace hello_csharp
{
    public class BindingExample
    {
        public void Run()
        {
            var binding = EzyBinding.builder()
                .addReflectionConverter<User>()
                .addReflectionConverter<Room>()
                .build();
            var user = new User
            {
                Name = "Monkey",
                Age = 29,
                Friend = "Fox"
            };

            var map = binding.marshall<EzyObject>(user);
            Console.WriteLine("user to map: " + map);

            var mappedUser = binding.unmarshall<User>(map);
            Console.WriteLine("map to user: " + mappedUser);

            var room = new Room()
            {
                Id = 1,
                Name = "Lobby",
                Users = new List<User>() {
                    new User {
                        Name = "User1",
                        Age = 18,
                        Friend = "Friend1",
                        BirthDate = new DateTime()
                    },
                    new User {
                        Name = "User2",
                        Age = 19,
                        Friend = "Friend2",
                        BirthDate = new DateTime()
                    }
                },
                NPCs = new Dictionary<string, User>() {
                    {
                        "NPC1", new User {
                            Name = "NPC1",
                            Age = 18,
                            Friend = "FriendN1",
                            BirthDate = new DateTime()
                        }
                    },
                    {
                        "NPC2", new User {
                            Name = "NPC2",
                            Age = 19,
                            Friend = "FriendN2",
                            BirthDate = new DateTime()
                        }
                    }
                }
            };

            var array = binding.marshall<EzyArray>(room);
            Console.WriteLine("room to array: " + array);

            var mappedRoom = binding.unmarshall<Room>(array);
            Console.WriteLine("map to room: " + mappedRoom);
        }
    }

    [EzyObjectBinding]
    public class User
    {
        public string Name { get; set; }
        public int Age { get; set; }
        [EzyValue("f")]
        public String Friend { get; set; }
        public DateTime BirthDate { get; set; }

        public override string ToString()
        {
            return "User (\n" +
                "\tName: " + Name +
                "\n\tAge: " + Age +
                "\n\tFriend: " + Friend +
                "\n\tBirthDate: " + BirthDate +
                "\n)";
        }
    }

    [EzyArrayBinding]
    public class Room
    {
        [EzyValue(0)]
        public long Id { get; set; }

        [EzyValue(1)]
        public String Name { get; set; }

        [EzyValue(2)]
        public IList<User> Users { get; set; }

        [EzyValue(3)]
        public IDictionary<String, User> NPCs { get; set; }

        public override string ToString()
        {
            return "Room (\n" +
                "\tId: " + Id +
                "\n\tName: " + Name +
                "\n\tUsers: " + Users +
                "\n\tNPCs: " + NPCs +
                "\n)";
        }
    }
}
