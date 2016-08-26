#include <Arduino.h>

class dmx
{
public:
    static void setPin(int pin);
    static void write(String command);
private:
    static void parseCommand(String command);
    static void update(int chan, int val);
    static void shiftOut(int pin, int theByte);
};

