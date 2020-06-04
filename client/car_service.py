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
    edit_entity_vars = [[]]
    add_entity_vars = [[]]
    for i in range(len(actions)):
        widgets = []
        variables = []
        for j in entity_params[i]:
            row = len(widgets)
            if j == "is foreign":
                variables.append(tk.BooleanVar())
                widgets.append(tk.Checkbutton(edit_entity_tabs[i], text=j, variable=variables[len(variables) - 1]))
                widgets[len(widgets) - 1].grid(row=row, column=0, columnspan=2)
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
    edit_id_label = tk.Label(remove_tab, text="Id:")
    edit_id_label.grid(column=1, row=0, padx=5, pady=5)
    edit_id_entry = tk.Entry(remove_tab, width=15)
    edit_id_entry.grid(column=2, row=0)

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
        entity_table_scrolls.append(ttk.Scrollbar(entity_tabs[0], orient="vertical", command=entity_tables[0].yview))
        entity_table_scrolls[i].grid(column=1, row=0)
        entity_tables[i].configure(yscrollcommand=entity_table_scrolls[i].set)
        for column in entity_params[i]:
            entity_tables[i].column(column, width=80, minwidth=80, stretch=tk.NO)
            entity_tables[i].heading(column, text=column)
        entity_tables[i].grid(column=0, row=0)
        entities_notebook.add(entity_tabs[i], text=entities[i])

    error_label = tk.Label(root, fg="red", text="errors")
    error_label.grid(column=0, row=1)

    root.mainloop()


url = "http://localhost:8080"

session = requests.Session()
token = str
# window.iconbitmap('resources/cs.ico')
# login()
main()
