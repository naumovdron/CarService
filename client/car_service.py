import tkinter as tk
from tkinter import ttk
import requests
from tkcalendar import DateEntry


def login():
    def login_button_clicked():
        global session
        global token
        json_params = {"userName": username.get(), "password": password.get()}
        try:
            response = session.post(url + "/login", json=json_params, timeout=timeout)
            if response.status_code == 403:
                error_label.configure(text="access denied")
            elif response.status_code == 500:
                error_label.configure(text="internal server problem")
            else:
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

    entities = ["Work", "Car", "Service", "Master"]
    entity_params = [("id", "date", "master id", "car id", "service id"),
                     ("id", "number", "color", "mark", "is foreign"),
                     ("id", "name", "our cost", "foreign cost"),
                     ("id", "name")]

    actions = ["Show", "Remove", "Edit", "Add"]

    actions_notebook = tk.ttk.Notebook(root)
    actions_notebook.grid(row=0, column=2)

    action_button = tk.Button(root, text=actions[0], width=20)
    action_button.grid(column=2, row=1)

    def actions_tab_changed(event):
        action_button["text"] = actions[actions_notebook.index(actions_notebook.select())]

    actions_notebook.bind("<<NotebookTabChanged>>", actions_tab_changed)

    edit_entity_tabs = [tk.Frame(actions_notebook, height=170, width=120) for __ in range(len(entities))]
    add_entity_tabs = [tk.Frame(actions_notebook, height=170, width=120) for __ in range(len(entities))]
    edit_entity_vars = []
    add_entity_vars = []
    for i in range(len(actions)):
        widgets = []
        variables = []
        for j in entity_params[i]:
            row = len(widgets)
            if j == "is foreign":
                variables.append(tk.BooleanVar())
                widgets.append(tk.Checkbutton(edit_entity_tabs[i], text=j, variable=variables[len(variables) - 1]))
                widgets[len(widgets) - 1].grid(row=row, column=0, columnspan=2)
            elif j == "date":
                widgets.append(tk.Label(edit_entity_tabs[i], text=j + ":"))
                widgets[len(widgets) - 1].grid(row=row, column=0)
                variables.append(DateEntry(edit_entity_tabs[i], width=12, date_pattern="yyyy-mm-dd"))
                variables[len(widgets) - 1].grid(row=row, column=1)
            else:
                widgets.append(tk.Label(edit_entity_tabs[i], text=j + ":"))
                widgets[len(widgets) - 1].grid(row=row, column=0)
                variables.append(tk.Entry(edit_entity_tabs[i], width=15))
                variables[len(widgets) - 1].grid(row=row, column=1)
        edit_entity_vars.append(variables)
        widgets = []
        variables = []
        for j in entity_params[i]:
            if j != "id":
                row = len(widgets)
                if j == "is foreign":
                    variables.append(tk.BooleanVar())
                    widgets.append(tk.Checkbutton(add_entity_tabs[i], text=j, variable=variables[len(variables) - 1]))
                    widgets[len(widgets) - 1].grid(row=row, column=0, columnspan=2)
                elif j == "date":
                    widgets.append(tk.Label(add_entity_tabs[i], text=j + ":"))
                    widgets[len(widgets) - 1].grid(row=row, column=0)
                    variables.append(DateEntry(add_entity_tabs[i], width=12, date_pattern="yyyy-mm-dd"))
                    variables[len(widgets) - 1].grid(row=row, column=1)
                else:
                    widgets.append(tk.Label(add_entity_tabs[i], text=j + ":"))
                    widgets[len(widgets) - 1].grid(row=row, column=0)
                    variables.append(tk.Entry(add_entity_tabs[i], width=15))
                    variables[len(widgets) - 1].grid(row=row, column=1)
        add_entity_vars.append(variables)

    find_tab = tk.Frame(actions_notebook, height=170, width=120)
    find_id_label = tk.Label(find_tab, text="Id:")
    find_id_label.grid(row=0, column=0)
    find_id_entry = tk.Entry(find_tab, width=15)
    find_id_entry.grid(row=0, column=1)
    show_all = tk.BooleanVar()
    show_all_checkbox = tk.Checkbutton(find_tab, text="Show all", variable=show_all)

    def show_all_checkbutton_changed():
        if show_all.get():
            find_id_entry["state"] = tk.DISABLED
        else:
            find_id_entry["state"] = tk.NORMAL
    show_all_checkbox["command"] = show_all_checkbutton_changed
    show_all_checkbox.grid(row=1, column=0, columnspan=2)

    remove_tab = tk.Frame(actions_notebook, height=170, width=120)
    remove_id_label = tk.Label(remove_tab, text="Id:")
    remove_id_label.grid(column=1, row=0, padx=5, pady=5)
    remove_id_entry = tk.Entry(remove_tab, width=15)
    remove_id_entry.grid(column=2, row=0)

    actions_notebook.add(find_tab, text="Find")
    actions_notebook.add(remove_tab, text="Remove")
    actions_notebook.add(edit_entity_tabs[0], text="Edit")
    actions_notebook.add(add_entity_tabs[0], text="Add")

    entities_notebook = tk.ttk.Notebook(root)

    def entities_tab_changed(event):
        actions_notebook.forget(3)
        actions_notebook.forget(2)
        actions_notebook.add(edit_entity_tabs[entities_notebook.index(entities_notebook.select())], text="Edit")
        actions_notebook.add(add_entity_tabs[entities_notebook.index(entities_notebook.select())], text="Add")

    entities_notebook.bind("<<NotebookTabChanged>>", entities_tab_changed)
    entities_notebook.grid(row=0, column=0)
    entity_tabs = []
    entity_tables = []
    entity_table_scrolls = []
    for i in range(len(entities)):
        entity_tabs.append(tk.Frame(entities_notebook, height=170, width=120))
        entity_tables.append(ttk.Treeview(entity_tabs[i], columns=entity_params[i], show='headings'))
        entity_table_scrolls.append(ttk.Scrollbar(entity_tabs[i], command=entity_tables[i].yview))
        entity_table_scrolls[i].grid(column=1, row=0)
        entity_tables[i].configure(yscrollcommand=entity_table_scrolls[i].set)
        for column in entity_params[i]:
            entity_tables[i].column(column, width=80, minwidth=80, stretch=tk.NO)
            entity_tables[i].heading(column, text=column)
        entity_tables[i].grid(column=0, row=0)
        entities_notebook.add(entity_tabs[i], text=entities[i])

    error_label = tk.Label(root, fg="red")
    error_label.grid(column=0, row=1)

    def action_button_clicked():
        global session
        error_label["text"] = ""
        action = actions_notebook.index(actions_notebook.select())
        entity = entities_notebook.index(entities_notebook.select())
        json_params = {}
        headers = {"Authorization": "Bearer " + token}
        response = None
        try:
            if action == 0:
                if show_all.get():
                    response = session.get(url + "/" + entities[entity].lower(), timeout=timeout, headers=headers)
                else:
                    response = session.get(url + "/" + entities[entity].lower() + "/" + find_id_entry.get(),
                                           timeout=timeout, headers=headers)
            elif action == 1:
                response = session.delete(url + "/" + entities[entity].lower() + "/" + remove_id_entry.get(),
                                          timeout=timeout, headers=headers)
            elif action == 2:
                if entity == 0:
                    json_params["date"] = edit_entity_vars[0][1].get()
                    json_params["masterId"] = edit_entity_vars[0][2].get()
                    json_params["carId"] = edit_entity_vars[0][3].get()
                    json_params["serviceId"] = edit_entity_vars[0][4].get()
                elif entity == 1:
                    json_params["num"] = edit_entity_vars[1][1].get()
                    json_params["color"] = edit_entity_vars[1][2].get()
                    json_params["mark"] = edit_entity_vars[1][3].get()
                    json_params["foreign"] = edit_entity_vars[1][4].get()
                elif entity == 2:
                    json_params["name"] = edit_entity_vars[2][1].get()
                    json_params["costOur"] = edit_entity_vars[2][2].get()
                    json_params["costForeign"] = edit_entity_vars[2][3].get()
                else:
                    json_params["name"] = edit_entity_vars[3][1].get()
                response = session.put(url + "/" + entities[entity].lower() + "/" + edit_entity_vars[entity][0].get(),
                                       timeout=timeout, headers=headers, json=json_params)
            else:
                if entity == 0:
                    json_params["date"] = add_entity_vars[0][0].get()
                    json_params["masterId"] = add_entity_vars[0][1].get()
                    json_params["carId"] = add_entity_vars[0][2].get()
                    json_params["serviceId"] = add_entity_vars[0][3].get()
                elif entity == 1:
                    json_params["num"] = add_entity_vars[1][0].get()
                    json_params["color"] = add_entity_vars[1][1].get()
                    json_params["mark"] = add_entity_vars[1][2].get()
                    json_params["foreign"] = add_entity_vars[1][3].get()
                elif entity == 2:
                    json_params["name"] = add_entity_vars[2][0].get()
                    json_params["costOur"] = add_entity_vars[2][1].get()
                    json_params["costForeign"] = add_entity_vars[2][2].get()
                else:
                    json_params["name"] = add_entity_vars[3][0].get()
                response = session.post(url + "/" + entities[entity].lower(),
                                        timeout=timeout, headers=headers, json=json_params)
            if response.status_code == 403:
                error_label["text"] = "access denied"
            elif response.status_code == 500:
                error_label["text"] = "internal server problem"
            elif response.status_code == 404:
                error_label["text"] = "not found"
            elif response.status_code == 400:
                error_label["text"] = "bad request"
            else:
                entity_tables[entity].delete(*entity_tables[entity].get_children())
                if action == 0:
                    if show_all.get():
                        for i in response.json():
                            entity_tables[entity].insert("", "end", values=tuple(i.values()))
                    else:
                        entity_tables[entity].insert("", "end", values=tuple(response.json().values()))
                elif action in [2, 3]:
                    entity_tables[entity].insert("", "end", values=tuple(response.json().values()))
        except requests.Timeout:
            error_label.configure(text="server does not response")
        except requests.ConnectionError:
            error_label.configure(text="connection error")
    action_button["command"] = action_button_clicked

    root.mainloop()


url = "http://localhost:8080"
timeout = 0.5
session = requests.Session()
token = ""
login()
