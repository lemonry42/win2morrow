
# 0. Wait for Connection from client.
# 1. Once client has connected, check credentials
# 2. Generate valid number
# 3. Send it back to client
# 4. Save it to storage

import socket
import random
import threading

SERVER_ADDRESS = '127.0.0.1'  # Server IP Address
SERVER_PORT = 12345           # Server port
BUFFER_SIZE = 1024            # Buffer size for receiving data


def handle_client(client_socket):
    try:
        # Authenticate client
        auth_message = client_socket.recv(BUFFER_SIZE).decode()
        if auth_message == 'AUTH':
            client_socket.sendall(b'OK')
            print("Client authenticated.")
        else:
            client_socket.sendall(b'FAIL')
            print("Client failed authentication.")
            client_socket.close()
            return

        # Wait for request for random number
        request_message = client_socket.recv(BUFFER_SIZE).decode()
        if request_message == 'GET_RANDOM_NUMBER':
            # Generate a random number
            random_number = str(random.randint(1, 100))
            client_socket.sendall(random_number.encode())
            print(f"Sent random number: {random_number}")
        else:
            print("Invalid request from client.")
    except socket.error as e:
        print(f"Error handling client: {e}")
    finally:
        client_socket.close()


def start_server():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind((SERVER_ADDRESS, SERVER_PORT))
    server_socket.listen(5)
    print(f"Server listening on {SERVER_ADDRESS}:{SERVER_PORT}")

    while True:
        client_socket, client_address = server_socket.accept()
        print(f"Accepted connection from {client_address}")
        client_handler = threading.Thread(target=handle_client, args=(client_socket,))
        client_handler.start()


if __name__ == '__main__':
    start_server()
