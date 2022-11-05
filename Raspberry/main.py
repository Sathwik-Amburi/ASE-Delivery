import datetime
import time

class LEDState:
    OFF = 0
    RED = 1
    GREEN = 2
    
class LightState:
    DARK = 0
    LIGHT = 1


class BoxState:
    CLOSED = 0
    OPENED = 1


def check_deliverer(deliverer_name: str) -> bool:
    pass


def check_customer(customer_name: str) -> bool:
    pass


def switch_led(state: LEDState) -> None:
    pass


def check_box_state() -> BoxState:
    pass

def update_box_status() -> None:
    """send to the backEnd 'The goods were taken' and so on"""
    pass
    
def blink_for(state: LEDState, duration: int) -> None:
    for _ in range(duration):
        switch_led(state)
        time.sleep(0.5)
        switch_led(LEDState.OFF)
        time.sleep(0.5)
        
def handle_user() -> bool:
    while datetime.now() - time_opened < 10: # 10s have passed since opening the box
        time.sleep(0.25)
                        
        if check_box_state() == BoxState.CLOSED: # box is closed
            update_box_status()
            return true
                
    return false
                    

def main_loop():
    while True:
        id, text = reader.read()
        if check_deliverer() or check_customer():
            time_opened = datetime.now()
            switch_led(LEDState.GREEN)
            
            if !handle_user():
                blink_for(LEDState.RED, 5)
                
            switch_led(LEDState.OFF)
                                


if __name__ == '__main__':
    main_loop()
