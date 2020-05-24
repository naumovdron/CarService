import PySimpleGUI as sg

import tkinter as tk

# sg.preview_all_look_and_feel_themes()
sg.theme('Reddit')

carLayout = [
    [sg.Text(text='number:', size=(10, 1)), sg.InputText(size=(20, 1))],
    [sg.Text(text='color:', size=(10, 1)), sg.InputText(size=(20, 1))],
    [sg.Text(text='mark:', size=(10, 1)), sg.InputText(size=(20, 1))],
    [sg.Checkbox(text='foreign', size=(10, 1))]
]
serviceLayout = [
    [sg.Text(text='name:', size=(10, 1)), sg.InputText(size=(20, 1))],
    [sg.Text(text='our\'s cost:', size=(10, 1)), sg.InputText(size=(20, 1))],
    [sg.Text(text='foreign\'s cost:', size=(10, 1)), sg.InputText(size=(20, 1))]
]
masterLayout = [
    [sg.Text(text='name:', size=(10, 1)), sg.InputText(size=(20, 1))]
]
workLayout = [
    [sg.Text(text='master id:', size=(10, 1)), sg.InputText(size=(20, 1))],
    [sg.Text(text='service id:', size=(10, 1)), sg.InputText(size=(20, 1))],
    [sg.Text(text='car id:', size=(10, 1)), sg.InputText(size=(20, 1))],
    [sg.CalendarButton(button_text='date', size=(10, 1)), sg.Text(text='date', size=(20, 1))]
]

tabs = sg.TabGroup([
    [sg.Tab('car', carLayout)], [sg.Tab('service', serviceLayout)],
    [sg.Tab('master', masterLayout)], [sg.Tab('work', workLayout)]
])

mainLayout = [
    [tabs, sg.Listbox(values=('show all', 'show', 'delete', 'update', 'add'), size=(10, 5))],
    [sg.Button(button_text='get', size=(10, 1))],
    [sg.Output(size=(50, 10))],
    [sg.Submit(), sg.Cancel()]
]

loginLayout = [
    [sg.Text(text='username:', size=(10, 1)), sg.InputText(size=(20, 1))],
    [sg.Text(text='password:', size=(10, 1)), sg.InputText(size=(20, 1), password_char='*')],
    [sg.Submit()]
]

window = sg.Window(title='login', layout=loginLayout)

while True:
    event, values = window.read()
    # print(event, values)
    if event in (None, 'Exit', 'Submit'):
        break

window.close()
window = sg.Window(title='car service', layout=mainLayout)

while True:
    event, values = window.read()
    # print(event, values)
    if event in (None, 'Exit', 'Cancel'):
        break

window.close()
