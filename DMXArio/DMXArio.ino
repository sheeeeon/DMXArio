#include "DmxSimple.h"

void setup() {
  DmxSimple.usePin(2);
  DmxSimple.maxChannel(16);
  Serial.begin(9600);
}

void loop() {
  int brightness;
  for (brightness = 0; brightness <= 255; brightness++) {

    DmxSimple.write(1, brightness);

    delay(100);
    Serial.println(brightness);
  }

}

