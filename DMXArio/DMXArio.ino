//dmxario arduino ver.

#include <SoftwareSerial.h> 
#include <string.h>
#include "pins_arduino.h"
#include "dmx.h"

dmx dmx;

int Tx=6;  
int Rx=7;  

SoftwareSerial mySerial(Tx, Rx);

int param[3] = {0,0,0}; //-> mode, sub, value.
int i = 0;
int arrayCount=0;

String command;

void setup() {
  Serial.begin(38400);   
  mySerial.begin(38400); 
  //DmxSimple.usePin(2);
  pinMode(2, OUTPUT);
  //DmxSimple.maxChannel(512);
  
}

void loop() {
  char tmp2;
  while (mySerial.available()) {
    tmp2 = mySerial.read();
    command += tmp2;
    if (tmp2 == '#') {
      Serial.print(command);
      execute(getParam(command));
      command = "";
      Serial.println();
      break;
    }
  }
}

int* getParam(String cmd) {
  char tmp[4] = "";
  int param[3] = {0,0,0};
  int eror = 3;
  if (cmd[0] == '+' && cmd[2] == ':') {
    param[0] = cmd[1];
    
  } else if (cmd[1] == ':') {
    param[0] = cmd[0];
    eror = 2;
  }
  
  int i;
  int tempInt;
  
  for (i = eror; i <= 10; i++) {
      if (cmd[i] == ':') {
        cmd.substring(eror, i+1).toCharArray(tmp, 4);
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
    dmx.dmxwrite(prt[1], prt[2]);
  } else if (prt[0] == 100) {
    delay(prt[2]);
  } else {
    return false;
  }
}

