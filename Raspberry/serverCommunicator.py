import os
from enum import Enum

import requests

import const


class ActorType(Enum):
    DELIVERER = 0
    CLIENT = 1
    ANY = 2
    DISPATCHER = 3;


class ServerCommunicator:
    def __init__(self, server_address: str, box_number: str):
        self.server_address = server_address
        self.session = requests.Session()
        self.data_params_id = {'boxNumber': box_number}
        self.header = {'Content-type': 'application/json'}

    def check_person_name(self, actor_id: str, a_type: ActorType, debug=False) -> (bool, str):
        if debug:
            user_set = {"lol", "kek", "Danya                                           "}
            return actor_id in user_set

        url = os.path.join(self.server_address, 'order/get-undeliv-order-by-box')
        print(f"url: {url}, json: {self.data_params_id}")
        response = self.session.post(url, json=self.data_params_id)
        print(f'INFO for check_person_name\nURL: "{url}", DATA: {self.data_params_id}\n'
              f'STATUS_CODE: {response.status_code}, TEXT: {response.text}\n')
        if response.status_code == 406:
            return False, None

        assert response.status_code == 200, \
            f'INFO: The response of the query "{url}", data: "{self.data_params_id}" is incorrect. ' \
            f'{response.status_code}: {response.text}'
        response_json = response.json()
        order_id = response_json['id']
        deliverer_id = response_json['deliverer']['id']
        client_id = response_json['client']['id']

        if a_type == ActorType.DELIVERER:
            result = (deliverer_id == actor_id)
        elif a_type == ActorType.CLIENT:
            result = (client_id == actor_id)
        elif a_type == ActorType.ANY:
            result = (deliverer_id == actor_id) or (client_id == actor_id)
        else:
            raise NotImplemented(f'The check for {a_type} is not implemented')

        return result, order_id

    def change_order_status(self, order_id: str, status_str: str) -> bool:
        # status_str is STATUS_DELIVERED or STATUS_ONITSWAY
        url = os.path.join(self.server_address, 'order')
        params = {'orderId': order_id, 'newOrderStatus': status_str}
        response = self.session.put(url, headers=self.header, json=params)
        print(f'INFO for change_order_status\nURL: "{url}", DATA: {params}\n'
              f'STATUS_CODE: {response.status_code}, TEXT: {response.text}\n')
        if response.status_code != 200:
            print(
                f'INFO: The response of the query "{url}" is incorrect. {response.status_code}: {response.text}')
            return False
        print(f'The statis "Delivered" is set to {order_id} order')
        return True


if __name__ == '__main__':
    # test ServerCommunicator
    serverCommunicator = ServerCommunicator(
        server_address="http://127.0.0.1:8080", box_number=const.BOX_NUMBER)

    kek1 = serverCommunicator.check_person_name(actor_id="63c1778662cd023293ebaaa1", a_type=ActorType.CLIENT)
    print(kek1)

    order_id = kek1[1]
    kek2 = serverCommunicator.change_order_status(order_id=order_id)
    print(kek2)
