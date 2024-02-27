from flask import Flask, render_template,request,redirect
import paho.mqtt.client as paho

app = Flask(__name__)
app.secret_key = 'chavebuesecreta'
#ao conectar ao servidor mqtt, subcrevemos o tópico
def on_connect(client, userdata, flags, rc):
    #print("Connected: ", str(rc))
    client.subscribe("ipt/iot")

#ao receber mensagens mqtt atualizamos a tag ou guardamos caso seja uma tag nova
def on_message(client, userdata, msg):
    print("mensagem: " + msg.payload.decode())
    tag = msg.payload.decode()
    if tag in load_tags():
        print("skipping, already saved")
    else:
        print("saving tag")
        write_tags(tag+";denied")
        print(load_tags())

#credenciais para conectar ao servidor mqtt
print("Starting connection")
broker="45.156.85.191"
port=46061
client = paho.Client()
client.username_pw_set("webserver", "iot2023pico")
client.on_message = on_message
client.on_connect = on_connect
client.loop_start()
client.connect(broker,port)

#função para editar tags
def change_access(tag,value):
    tags = load_tags()
    print("changing value of " + tag + " from " + tags[tag] + " t>    tags[tag] = value  # Update the value for the given tag
    client.publish("ipt/iot",str(tag)+";"+tags[tag])

    with open("tags", "w") as file:
        for key, value in tags.items():
            file.write(f"{key};{value}\n")
#função para carregar tags
def load_tags():
    print("loading tags\n")
    tags = {}
    with open("tags", "r") as file:
        for line in file:
            values = line.strip().split(";")
            key = values[0]
            value = values[1] if len(values) > 1 else ""
            tags[key] = value
return tags
#função para guardar tags
def write_tags(value):
    with open("tags", "a") as file:
        file.write(value + "\n")

#carregamos a página com as tags passadas por parâmetro com o flask
@app.route('/')
def hello():
    return render_template('index.html', tags = load_tags() )

#ao receber um pedido para alterar o estado de uma tag, recebemos os dados pelo form e redirecionamos para a função
@app.route('/change-access', methods=['POST'])
def change_access_route():
    print("------------NEW REQUEST-------------")
    tag = request.form.get('tag')
    value = request.form.get('value')
    print("redirect for " + tag + " and " + value + "\n")
    change_access(tag, value)
return redirect('/')

if __name__ == '__main__':
    app.run(host="0.0.0.0", port="8080", debug=True)