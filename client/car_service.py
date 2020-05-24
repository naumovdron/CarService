import tkinter as tk
import requests


def login():
    def login_button_clicked():
        print(username.get() + password.get())
        username_label.configure(text=username.get() + password.get())

    window = tk.Tk()
    window.title("Login")
    window.geometry('160x80')

    username = tk.StringVar()
    password = tk.StringVar()

    username_label = tk.Label(window, text="Username:")
    username_label.grid(column=0, row=0)

    username_entry = tk.Entry(window, width=15, textvariable=username)
    username_entry.grid(column=1, row=0)

    password_label = tk.Label(window, text="Password:")
    password_label.grid(column=0, row=1)

    password_entry = tk.Entry(window, width=15, show="*", textvariable=password)
    password_entry.grid(column=1, row=1)

    login_button = tk.Button(window, text="Login", command=login_button_clicked)
    login_button.grid(column=0, row=2)

    window.mainloop()


def main():
    window = tk.Tk()
    window.title("Car Service")
    window.geometry('160x80')
    window.mainloop()


url = "http://localhost:8080"
json_params = {"userName": "admin", "password": "admin"}
response = requests.post(url + "/login", json=json_params)
if response.status_code == 403:
    print("access denied")
elif response.status_code == 500:
    print("internal server problem")
else:
    print(response.json())
    # print(response.text)
    # print(response.headers)
# login()
# main()

