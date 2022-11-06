from utils import HardwareController


def rewrite_name() -> str:
    controller = HardwareController()
    while True:
        name = controller.read_RFID_name()
        print(f'name=|{name}|')
        do_change = (input("change the name? Press 'y' for yes: ") == 'y')
        if do_change:
            new_name = input('type new name: ')
            controller.write_RFID_name(new_name)


if __name__ == '__main__':
    rewrite_name()
