import os
from enum import Enum

import requests

import const


class ActorType(Enum):
    DELIVERER = 0
    CLIENT = 1
    ANY = 2


class ServerCommunicator:
    def __init__(self, server_address: str, rasp_name: str, rasp_pass: str):
        self.server_address = server_address
        self.session = requests.Session()
        self.post_params_id = {'delivererId': rasp_name}

        # for authentication
        # self.session.auth = (rasp_name, rasp_pass)
        # response = self.session.get(url, verify=False)
        # assert response.status_code == 200, (
        #     f'Server addr, rasp_name or rasp_pass is incorrect. The GET request to "{url}" with '
        #     f'user "{rasp_name}" and pass "{rasp_pass}" returned {response.status_code} status code'
        # )

    def check_person_name(self, actor_id: str, a_type: ActorType, debug=False) -> (bool, str):
        if debug:
            user_set = {"lol", "kek", "Danya                                           "}
            return actor_id in user_set

        url = os.path.join(self.server_address, 'Order/getUndelivOrderByDeliverer')
        response = self.session.post(url, data=self.post_params_id)
        print(url, self.post_params_id)  # todo remove
        print(response.status_code, response.text)  # todo remove
        if response.status_code == 406:
            return False, None

        assert response.status_code == 200, \
            f'INFO: The response of the query "{url}", data: "{self.post_params_id}" is incorrect. ' \
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

    def set_order_delivered(self, order_id: str) -> bool:
        url = os.path.join(self.server_address, 'Order/updateOrderStatus')
        params = {'orderId': order_id, 'newOrderStatus': 'Delivered'}
        response = self.session.put(url, data=params)
        print(url, params)  # todo remove
        print(response.status_code, response.text)  # todo remove
        if response.status_code != 200:
            print(
                f'INFO: The response of the query "{url}" is incorrect. {response.status_code}: {response.text}')
            return False
        print(f'The statis "Delivered" is set to {order_id} order')
        return True


if __name__ == '__main__':
    # test ServerCommunicator
    serverCommunicator = ServerCommunicator(
        server_address=const.SERVER_ADDRESS, rasp_name=const.RASP_NAME, rasp_pass=const.RASP_PASS)

    kek1 = serverCommunicator.check_person_name(actor_id="638f0fc1b39edb53d3f173d4", a_type=ActorType.CLIENT)
    print(kek1)

    order_id = kek1[1]
    kek2 = serverCommunicator.set_order_delivered(order_id=order_id)
    print(kek2)
