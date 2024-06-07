
# 0. Ensure Conenction to Server, otherwise give Warning
# 1. Connect to Server, authenticate
# .. Ask for random Number from Server
# 2. Receive Random Number from Server
# 3. Write it to file

import socket
import sys

SERVER_ADDRESS = '192.168.0.100'  # Server IP address
SERVER_PORT = 12345               # Server port
BUFFER_SIZE = 1024                # Buffer size for receiving data
FILENAME = 'result.txt'


def connect_to_server():
    try:
        # Create a socket object
        client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        client_socket.connect((SERVER_ADDRESS, SERVER_PORT))
        print("Connected to server.")
        return client_socket
    except socket.error as e:
        print(f"Warning: Unable to connect to the server. Error: {e}")
        sys.exit(1)


def authenticate(socket):
    try:
        # Send authentication message (example: 'AUTH' command)
        socket.sendall(b'AUTH')
        response = socket.recv(BUFFER_SIZE).decode()
        if response == 'OK':
            print("Authentication successful.")
        else:
            print("Authentication failed.")
            socket.close()
            sys.exit(1)
    except socket.error as e:
        print(f"Error during authentication: {e}")
        socket.close()
        sys.exit(1)


def request_random_number(socket):
    try:
        # Send request for random number
        socket.sendall(b'GET_RANDOM_NUMBER')
        random_number = socket.recv(BUFFER_SIZE).decode()
        print(f"Received random number: {random_number}")
        return random_number
    except socket.error as e:
        print(f"Error while requesting random number: {e}")
        socket.close()
        sys.exit(1)


def write_to_file(data):
    try:
        with open(FILENAME, 'w') as file:
            file.write(data)
            print(f"Random number written to {FILENAME}")
    except IOError as e:
        print(f"Error writing to file: {e}")


def main():
    client_socket = connect_to_server()
    authenticate(client_socket)
    random_number = request_random_number(client_socket)
    write_to_file(random_number)
    client_socket.close()


if __name__ == '__main__':
    main()
