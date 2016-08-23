//dmxario arduino ver.

#include <SoftwareSerial.h> 
#include <string.h>
#include "pins_arduino.h"


#define LED_GND 8
#define LED_B   9
#define LED_G   10
#define LED_R   11
 
int Tx=6;  
int Rx=7;  

int sig = 2;

byte value[64];

SoftwareSerial mySerial(Tx, Rx);



//int param[3] = {0,0,0}; //-> mode, sub, value.
int i = 0;
int arrayCount=0;
bool flag = true;



void setup() {
  pinMode(LED_R, OUTPUT);
  pinMode(LED_G, OUTPUT);
  pinMode(LED_B, OUTPUT);
  pinMode(LED_GND, OUTPUT);
  digitalWrite(LED_GND, LOW);
  Serial.begin(38400);   
  mySerial.begin(38400); 
  //DmxSimple.usePin(2);
  pinMode(sig, OUTPUT);
  //DmxSimple.maxChannel(512);
  
}

void loop() {
  String command;
  char tmp2;
  while (mySerial.available()) {
    tmp2 = mySerial.read();
    command += tmp2;
    if (tmp2 == '#') {
      Serial.print(command);
      execute(getParam(command));
      //mySerial.begin(38400); 
      command = "";
      flag = true;
      Serial.println();
      break;
    }
  }
  if (!mySerial.available() && flag) {
    flag=false;
  }
  
}

int* getParam(String cmd) {
  char tmp[4] = "";
  int param[3] = {0,0,0};
  if (cmd[0] == '+' && cmd[2] == ':') {
    param[0] = cmd[1];

  }
  int i;
  int tempInt;
  
  
  for (i = 3; i <= 10; i++) {
    if (cmd[i] == ':') {
      cmd.substring(3, i+1).toCharArray(tmp, 4);
      param[1] = atoi(tmp);
      break;
    }
  }
  
  for (int j = i+1; j <= 11; j++) {
    if (cmd[j] == '#') {
      cmd.substring(i+1, j+1).toCharArray(tmp, 4);
      param[2] = atoi(tmp);
      break;
    }
  }
  Serial.print(" -- ");
  Serial.print(param[0]);
  Serial.print("+");
  Serial.print(param[1]);
  Serial.print("+");
  Serial.print(param[2]);
  return param;
  
}

bool execute(int prt[3]) {
  if (prt[0] == 101 && prt[1] > 0) {
    Serial.print("%");
    dmxwrite(prt[1], prt[2]);
  } else if (prt[0] == 100) {
    delay(prt[2]);
  } else {
    return false;
  }
}

void dmxwrite(int chan, int val) {
  digitalWrite(sig, LOW);

  value[chan-1] = val;
  
  dmxexecute();
}

void dmxexecute() {
  
  shiftDmxOut(sig, 0);
  
  for (int i = 1; i <= 64; i++) {
    shiftDmxOut(sig, value[i-1]);
  }
  Serial.print(" ------- W");
}

void shiftDmxOut(int pin, int theByte)
{
  int port_to_output[] = {
    NOT_A_PORT,
    NOT_A_PORT,
    _SFR_IO_ADDR(PORTB),
    _SFR_IO_ADDR(PORTC),
    _SFR_IO_ADDR(PORTD)
    };

    int portNumber = port_to_output[digitalPinToPort(pin)];
  int pinMask = digitalPinToBitMask(pin);

  // the first thing we do is to write te pin to high
  // it will be the mark between bytes. It may be also
  // high from before
  _SFR_BYTE(_SFR_IO8(portNumber)) |= pinMask;
  delayMicroseconds(10);

  // disable interrupts, otherwise the timer 0 overflow interrupt that
  // tracks milliseconds will make us delay longer than we want.
  cli();

  // DMX starts with a start-bit that must always be zero
  _SFR_BYTE(_SFR_IO8(portNumber)) &= ~pinMask;

  // we need a delay of 4us (then one bit is transfered)
  // this seems more stable then using delayMicroseconds
  asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");
  asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");

  asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");
  asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");

  asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");
  asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");

  for (int i = 0; i < 8; i++)
  {
    if (theByte & 01)
    {
      _SFR_BYTE(_SFR_IO8(portNumber)) |= pinMask;
    }
    else
    {
      _SFR_BYTE(_SFR_IO8(portNumber)) &= ~pinMask;
    }

    asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");
    asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");

    asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");
    asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");

    asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");
    asm("nop\n nop\n nop\n nop\n nop\n nop\n nop\n nop\n");

    theByte >>= 1;
  }

  // the last thing we do is to write the pin to high
  // it will be the mark between bytes. (this break is have to be between 8 us and 1 sec)
  _SFR_BYTE(_SFR_IO8(portNumber)) |= pinMask;

  // reenable interrupts.
  sei();
}

