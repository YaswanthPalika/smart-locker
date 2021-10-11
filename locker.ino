# include<ESP8266WiFi.h>
# include<FirebaseArduino.h>
# define FIREBASE_HOST "locker-16971.firebaseio.com"
# define FIREBASE_AUTH "30n5Zg94JqakeJyH1OJ6Rqrc3EW3H1tvNNo3Mnvn"
# define WIFI_SSID " yas "
# define WIFI_PASS "candy007"
String led1_Status = "";
String led2_Status = "";
int led1 =5;
int led2 =4;
void setup ()
{
Serial.begin (115200) ;
delay (1000) ;
pinMode ( led1 , OUTPUT ) ;
pinMode ( led2 , OUTPUT ) ;
WiFi.begin(WIFI_SSID , WIFI_PASS ) ;
Serial.print (" connecting to ") ;
Serial.print ( WIFI_SSID ) ;
while(WiFi.status() != WL_CONNECTED )
{
Serial . print (".") ;
delay (500) ;
}
Serial . println (" all set , you are connected to :") ;
Serial . print ( WIFI_SSID ) ;
Firebase . begin ( FIREBASE_HOST , FIREBASE_AUTH ) ;
}
void loop ()
{
led1_Status = Firebase.getString ("LOCKER 1") ;
led1_Status.toUpperCase();
if ( led1_Status == " OPEN ")
{
Serial . println (" led1 is on ") ;
digitalWrite ( led1 , HIGH ) ;
}
else if ( led1_Status == " CLOSE ")
{
Serial . println (" led1 is off ") ;
digitalWrite ( led1 , LOW ) ;
}
else
{ Serial . println (" please check what you have typed ") ;
}
}
