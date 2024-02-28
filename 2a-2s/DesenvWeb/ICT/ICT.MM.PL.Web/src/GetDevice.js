import {useState, useEffect } from 'react'; 
import { Link } from 'react-router-dom';

function Devices() {

    const [devices, setDevices] = useState([]);

    function GetDevices() {

        fetch("https://localhost:7207/Device")
            .then((res) => res.json())
            .then((data) => {
                setDevices(data)
                console.log(data)
            });
    }

    function eliminarDevice(id) {
        const device = {id}
        fetch("https://localhost:7207/Device", {
            method: 'DELETE',
            headers: { "Content-Type" : "application/json" },
            body: JSON.stringify(device)
        }).then(() => {
            console.log('Dispositivo Eliminado!')
            GetDevices()
        })
    }
    


    useEffect(() => {
        GetDevices();
    }, []);


        return (
        <div>
            <a href="https://localhost:7207/swagger/index.html" target="_blank">
                <button type="button">Swagger</button>
            </a>
            <button onClick={GetDevices}>Listar Dispositivos</button>
            <Link to="/criarDevice">
                    <button>Criar Novo Dispositivo</button>
            </Link>
            <table className="table table-striped">
                <tbody>
                    <tr>
                        <th>ID</th>
                        <th>DeviceType</th>
                        <th>Nome</th>
                        <th>Descrição</th>
                        <th>Manufacture Date</th>
                        <th>Last Maintenance Date</th>
                        <th>Manufactured By</th>
                        <th>Created By</th>
                        <th>Created Date</th>
                        <th>Modified By</th>
                        <th>Modified Date</th>
                    </tr>
                    {(devices.items)?.map(device => (
                        <tr key={device.id}>
                            <td>{device.id}</td>
                            <td>{device.id_DeviceType}</td>
                            <td>{device.name}</td>
                            <td>{device.description}</td>
                            <td>{device.manufacturedDate}</td>
                            <td>{device.lastMaintenanceDate}</td>
                            <td>{device.manufacturedBy}</td>
                            <td>{device.createdBy}</td>
                            <td>{device.createdDate}</td>
                            <td>{device.modifiedBy}</td>
                            <td>{device.modifiedDate}</td>
                            <td><Link to={{
                                pathname: "/editarDevices",
                                state: {device}
                            }} class="btn btn-info" role="button">Editar</Link></td>
                            <td><button class="btn btn-info" onClick={() => eliminarDevice(device.id)}>Eliminar</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>)
}

export default Devices;