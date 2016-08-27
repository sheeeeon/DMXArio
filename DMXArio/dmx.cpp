#include "dmx.h" 
#include "pins_arduino.h" 

#pragma region 상수

const char COMMAND_DELIMITER = ':';
const char COMMAND_TOKEN = '#';
const char COMMAND_FTOKEN = '+';

#pragma endregion


#pragma region 변수

byte value[512] = {0, };
int DMX_PIN;

#pragma endregion


#pragma region 사용자 함수

void dmx::setPin(int _pin)
{
    DMX_PIN = _pin;
    pinMode(_pin, OUTPUT);
}

void dmx::write(String command)
{
    Serial.print(command);
    int *CommandValue = dmx::parseCommand(command);
    dmx::execute(CommandValue);

}

#pragma endregion


#pragma region 내부 함수

int * dmx::parseCommand(String command)
{
    int *param;
    char tmp[4];

    
    int i;
    for (i = 3; i <= 6; i++) 
    {
        if (command[i] == COMMAND_DELIMITER) 
        {
            command.substring(3, i+1).toCharArray(tmp,4);
            param[1] = atoi(tmp);
            break;
        }
        else
        {
            
        }
    }

    for (int j = i+1; j <= 11; j++) 
    {
        if (command[j] == COMMAND_TOKEN) 
        {
            command.substring(i+1, j+1).toCharArray(tmp,4);
            param[2] = atoi(tmp);
            break;
        }
    }
    
    //?
    param[0] = command[1];

    Serial.print(" : ");
    Serial.print(param[0]);
    Serial.print(", ");
    Serial.print(param[1]);
    Serial.print(", ");
    Serial.print(param[2]);
    Serial.println();

    return param;
}

void dmx::execute (int *CommandValue) 
{
    if (CommandValue[0] == 'e') 
    {
        dmx::update(CommandValue[1], CommandValue[2]);
    } else if (CommandValue[0] == 'd')
    {
        delay(CommandValue[2]);
    }
}

void dmx::update(int chan, int val) 
{
    digitalWrite(DMX_PIN, LOW);
    delay(1);

    dmx::shiftOut(DMX_PIN, 0);
    value[chan-1] = val;
    for (int i = 0; i < 32; i++) 
    {
        dmx::shiftOut(DMX_PIN, value[i]);
    }
    Serial.print(" : ");
    Serial.print(chan);
    Serial.print(", ");
    Serial.println(val);
}

void dmx::shiftOut(int pin, int theByte)
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

#pragma endregion
