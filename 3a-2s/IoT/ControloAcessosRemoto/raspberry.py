import machine
from machine import Pin
from machine import PWM
from rfidReader import MFRC522
import wifimgr
import network
import time
from umqtt.simple import MQTTClient

#inicializamos a conexão wifi
wlan = wifimgr.get_connection()
if wlan is None:
    print("Could not initialize the network connection.")
    while True:
        pass  
print("IP: ", wlan.ifconfig()[0])

#credenciais para conectar ao servidor mqtt
mqtt_server = '45.156.85.191'
client_id = 'Pico'
client = MQTTClient(client_id, mqtt_server, port=46061,user="webserver", password="iot2023pico", keepalive=60)
#inicializamos o sensor RC522
reader = MFRC522(spi_id=0,sck=6,miso=4,mosi=7,cs=5,rst=22)
#Pins do led
redPin=13
greenPin=14
bluePin=15
#Inicializar o buzzer
buzzer = PWM(Pin(19))

#inicializar o led
redLed=PWM(Pin(redPin))
greenLed=PWM(Pin(greenPin))
blueLed=PWM(Pin(bluePin))
redLed.freq(1000)
greenLed.freq(1000)
blueLed.freq(1000)

#função para guardar tags no ficheiro
def save_tag(tag):
     with open("tags", "a") as file:
        file.write(tag + "\n")
     tags = load_tags()

#função para editar uma tag guardada
def change_access(tag, value):
    tags = load_tags()
    tags[tag] = value  # Update the value for the given tag

    with open("tags", "w") as file:
        for key, value in tags.items():
            print(key)
            file.write(key +  ';' + value  + '\n')

#função para carregar as tags guardadas
def load_tags():
    tags = {} 
    with open("tags", "r") as file:
        for line in file:
            values = line.strip().split(";")
            key = values[0]
            value = values[1] if len(values) > 1 else ""
            tags[key] = value
    return tags

#função que recebe as mensagens por mqtt
def on_message(topic, msg):
    message = msg.decode('UTF-8')
    values = message.strip().split(";")
    print("Mensagem recebida no topico: " + topic.decode('UTF-8')) 
    print("Mensagem: " + message)
    if "denied" in values[1] or "approved" in values[1]:
        change_access(values[0], values[1])
        print('value' + values[1])
    tags=load_tags()
    print(tags)

#definições do cliente mqtt
client.set_callback(on_message)
client.connect()
client.subscribe("ipt/iot", qos=0)
print("Aproxima a Tag \n")
reader.init()
#variáveis para controlar os cartões passados
prev_card = None 
card_processed = False

tags = load_tags()

#espera leitura de uma tag
while True:
    try:
        client.check_msg()
    except Exception:
        continue 
    (stat, tag_type) = reader.request(reader.REQIDL)
    if stat == reader.OK:
        (stat, uid) = reader.SelectTagSN()
        if stat == reader.OK:
            card = int.from_bytes(bytes(uid), "little", False)
            
            if card != prev_card:  # vê se é uma tag nova
                prev_card = card 
                card_processed = False 
                
            if not card_processed:
                card_processed = True
                print("TAG ID:", card)
                if str(card) not in tags: #caso a tag seja nova, atualizamos o ficheiro e enviamos para o webserver
                    print(tags)
                    save_tag(str(card) + ";denied")
                    tags=load_tags()
                    client.publish('ipt/iot', str(card), qos=0)
                    print('abs')
                    redLed.duty_u16(0)    
                    greenLed.duty_u16(0)      
                    blueLed.duty_u16(65535)       
                else: #caso não seja nova alteramos o estado do led e do buzzer
                    tags=load_tags()
                    tag_value = tags.get(card)
                    if tag_value == "denied":
                        redLed.duty_u16(65535)    
                        greenLed.duty_u16(0)      
                        blueLed.duty_u16(0)       
                        buzzer.freq(200)
                        buzzer.duty_u16(1000)
                        time.sleep(0.5)
                        buzzer.duty_u16(0)
                    elif tag_value == "approved":
                        tags=load_tags()
                        redLed.duty_u16(0)        
                        greenLed.duty_u16(65535)  
                        blueLed.duty_u16(0)       
                        buzzer.freq(1000)
                        buzzer.duty_u16(1000)
                        time.sleep(0.5)
                        buzzer.duty_u16(0)
            else:
                tag_value = tags.get(str(card))
                print(tag_value)
                if tag_value == "denied":
                    tags=load_tags()
                    print('recusado')
                    redLed.duty_u16(65535)    
                    greenLed.duty_u16(0)      
                    blueLed.duty_u16(0)       
                    buzzer.freq(200)
                    buzzer.duty_u16(1000)
                    time.sleep(0.5)
                    buzzer.duty_u16(0)
                elif tag_value == "approved":
                    tags=load_tags()
                    print('aprovado')
                    redLed.duty_u16(0)        
                    greenLed.duty_u16(65535)  
                    blueLed.duty_u16(0)       
                    buzzer.freq(1000)
                    buzzer.duty_u16(1000)
                    time.sleep(0.5)
                    buzzer.duty_u16(0)
           
