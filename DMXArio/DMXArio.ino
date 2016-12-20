//dmxario arduino ver.
/*
 */

#define BLUETOOTH_VCC 8
#define BLUETOOTH_GND 9
#define BLUETOOTH_TX_PIN 11
#define BLUETOOTH_RX_PIN 10
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
    pinMode(13, OUTPUT);
    digitalWrite(13, HIGH);
    pinMode(12, OUTPUT);
    digitalWrite(12, LOW);
    dmx.setPin(DMX_TX_PIN);
    Serial.begin(38400);
    Bluetooth.begin(38400);
}

void loop() 
{
    char tmp;
    if (Bluetooth.available()) 
    {
        tmp = Bluetooth.read();
        if (tmp != "\n" && tmp != ' ')
            command += tmp;
    }
    if (tmp == '#' && command.length() >= 7) {
        dmx.write(command);
        command = "";
    }
}
