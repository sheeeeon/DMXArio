#include <Arduino.h>

class dmx
{
public:
    static void setPin(int pin);
    static void write(String command);
private:
    static int * parseCommand(String command);
    static void execute(int *CommandValue);
    static void update(int chan, int val);
    static void shiftOut(int pin, int theByte);
};

