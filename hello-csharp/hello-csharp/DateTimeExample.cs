using System;
using System.Threading;
using com.tvd12.ezyfoxserver.client.io;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.binding;

namespace hello_csharp
{
    public class DateTimeExample
    {
        public void Run()
        {
            Thread.Sleep(10);
            DateTime now = DateTime.Now;
            Thread.Sleep(100);
            long offset = EzyDateTimes.getOffsetMillis(now, DateTime.Now);
            Console.WriteLine("offset: " + offset);
            Console.WriteLine("status int: " + (int)EzySocketStatus.CONNECTED);
            EzyDateTimeConverter dateTimeConverter = new EzyDateTimeConverter();
            string str = "2021-01-02T03:04:05.006";
            DateTime dateTime = (DateTime)dateTimeConverter.read(str, null);
            Console.WriteLine("dateTime: " + dateTime);
        }
    }
}
