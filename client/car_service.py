import tkinter as tk
from tkinter import ttk
import requests


def login():
    def login_button_clicked():
        global session
        global token
        json_params = {"userName": username.get(), "password": password.get()}
        try:
            response = session.post(url + "/login", json=json_params, timeout=0.01)
            if response.status_code == 403:
                error_label.configure(text="access denied")
            elif response.status_code == 500:
                error_label.configure(text="internal server problem")
            else:
                print(response.json())
                token = response.json()['token']
                root.destroy()
                main()
        except requests.Timeout:
            error_label.configure(text="server does not response")
        except requests.ConnectionError:
            error_label.configure(text="connection error")

    root = tk.Tk()
    root.title("Login")
    root.resizable(False, False)
    root.overrideredirect = False
    login_frame = tk.Frame(root, height=170, width=120)
    login_frame.pack_propagate(False)
    login_frame.grid(row=0, column=0)
    username = tk.StringVar()
    password = tk.StringVar()
    username_label = tk.Label(login_frame, text="Username:")
    username_label.grid(column=0, row=0, padx=5, pady=5)
    username_entry = tk.Entry(login_frame, width=15, textvariable=username)
    username_entry.grid(column=1, row=0)
    username_entry.focus()
    password_label = tk.Label(login_frame, text="Password:")
    password_label.grid(column=0, row=1, pady=5)
    password_entry = tk.Entry(login_frame, width=15, show="*", textvariable=password)
    password_entry.grid(column=1, row=1)
    error_label = tk.Label(login_frame, fg="red")
    error_label.grid(column=0, row=2, padx=5, columnspan=2)
    login_button = tk.Button(login_frame, text="Login", command=login_button_clicked, width=15)
    login_button.grid(column=0, row=3, padx=5, pady=5, columnspan=2)
    root.mainloop()


def main():
    root = tk.Tk()
    root.title("Car Service")
    root.resizable(False, False)
    tabs = tk.ttk.Notebook(root)

    car_tab = tk.Frame(tabs, height=170, width=120)
    id_label = tk.Label(car_tab, text="Id:")
    id_label.grid(column=1, row=0, padx=5, pady=5)
    id_entry = tk.Entry(car_tab, width=15)
    id_entry.grid(column=2, row=0)
    number_label = tk.Label(car_tab, text="Number:")
    number_label.grid(column=1, row=1, padx=5, pady=5)
    number_entry = tk.Entry(car_tab, width=15)
    number_entry.grid(column=2, row=1)
    color_label = tk.Label(car_tab, text="Color:")
    color_label.grid(column=1, row=2, padx=5, pady=5)
    color_entry = tk.Entry(car_tab, width=15)
    color_entry.grid(column=2, row=2)
    mark_label = tk.Label(car_tab, text="Mark:")
    mark_label.grid(column=1, row=3, padx=5, pady=5)
    mark_entry = tk.Entry(car_tab, width=15)
    mark_entry.grid(column=2, row=3)
    is_foreign_checkbox = tk.Checkbutton(car_tab, text="Is foreign")
    is_foreign_checkbox.grid(column=1, row=4, columnspan=2)
    error_label = tk.Label(car_tab, fg="red")
    error_label.grid(column=1, row=5, columnspan=2)
    action_button = tk.Button(car_tab, text="Action", width=20)
    action_button.grid(column=1, row=6, columnspan=2)

    car_columns = ("id", "number", "color", "mark", "is foreign")
    car_table = ttk.Treeview(car_tab, columns=car_columns, show='headings')
    for column in car_columns:
        car_table.heading(column, text=column)
    car_table.grid(column=0, row=0, rowspan=7)

    work_tab = tk.Frame(tabs, height=170, width=120)

    service_tab = tk.Frame(tabs, height=170, width=120)

    master_tab = tk.Frame(tabs, height=170, width=120)

    tabs.add(work_tab, text="Works")
    tabs.add(car_tab, text="Cars")
    tabs.add(service_tab, text="Services")
    tabs.add(master_tab, text="Masters")
    tabs.grid(row=0, column=0)
    root.mainloop()


url = "http://localhost:8080"

session = requests.Session()
token = str
# window.iconbitmap('resources/cs.ico')
# login()
main()
