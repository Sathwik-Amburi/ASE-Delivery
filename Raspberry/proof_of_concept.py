import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522
from time import sleep

user_set = {"lol", "kek", "Danya                                           "}
leg_red = 20
leg_green = 21

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(leg_red, GPIO.OUT, initial=GPIO.LOW)
GPIO.setup(leg_green, GPIO.OUT, initial=GPIO.LOW)


def switch_on(leg):
    GPIO.output(leg, GPIO.HIGH)
    sleep(1)
    GPIO.output(leg, GPIO.LOW)


# to check RFID scanner
# ------------------
reader = SimpleMFRC522()
try:
    while True:
        id, text = reader.read()
        print(f"id='{id}', text='{text}'")
        if text in user_set:
            switch_on(leg_green)
        else:
            switch_on(leg_red)
except KeyboardInterrupt:
    # GPIO must be cleaned up once you e x i t the s c r i p t
    # Otherwise , other s c r i p t s may not work as you expect
    GPIO.cleanup()
    raise
# ---------
#


# to check light sensor
# --------------------
# leg_sensor = 17
# GPIO.setmode(GPIO.BCM)
# GPIO.setup(leg_sensor, GPIO.IN)
# while True:
#     result = GPIO.input(leg_sensor)
#     print(result)
#     if result > 0.5:
#         switch_on(leg_green)
#     else:
#         switch_on(leg_red)
# ------------------
#
