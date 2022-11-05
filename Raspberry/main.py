class LEDState:
    TURN_OFF = 0
    RED = 1
    GREEN = 2


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


def main_loop():
    pass


if __name__ == '__main__':
    main_loop()
