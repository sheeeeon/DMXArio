//dmxario arduino ver.
/*
 */
 
#define BLUETOOTH_TX_PIN 6
#define BLUETOOTH_RX_PIN 7
#define DMX_TX_PIN 2

#include <SoftwareSerial.h> 
#include <string.h>
#include "pins_arduino.h"
#include "dmx.h"


dmx dmx;
SoftwareSerial Bluetooth (BLUETOOTH_TX_PIN,BLUETOOTH_RX_PIN);


//변수
static String command = "";

void setup() 
{
    dmx.setPin(DMX_TX_PIN);
    Serial.begin(38400);
    Bluetooth.begin(38400);
}

void loop() 
{
    while (Bluetooth.available()) 
    {
        char tmp = Bluetooth.read();
        
        if (tmp == '#')
        {
            dmx.write(command+'#');
            command = "";
        }
        else
        {
            command += tmp; 
        }
    }
}
