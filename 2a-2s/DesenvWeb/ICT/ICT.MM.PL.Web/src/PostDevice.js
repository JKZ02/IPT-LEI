import { useState, useEffect } from "react";
import { Link, useHistory } from 'react-router-dom';

const CreateDevice = () => {

    let history = useHistory()

    const [deviceTypes, setDeviceTypes] = useState([])
    const [id_DeviceType, setIdDeviceType] = useState({})
    const [name, setName] = useState('Device')
    const [description, setDescription] = useState('Description')
    const [manufacturedDate, setManufacturedDate] = useState('')
    const [lastMaintenanceDate, setLastMaintenanceDate] = useState('')
    const [maintenanceDueDate, setMaintenanceDueDate] = useState('')
    const [manufacturedBy, setManufacturedBy] = useState('')
    const [createdBy, setCreatedBy] = useState('')
    const [createdDate, setCreatedDate] = useState('')
    const [modifiedBy, setModifiedBy] = useState('')
    const [modifiedDate, setModifiedDate] = useState('')


    const handleSubmit = (e) => {
        e.preventDefault()
        const device = { id_DeviceType, name, description, manufacturedDate, lastMaintenanceDate, maintenanceDueDate, manufacturedBy, createdBy, createdDate, modifiedBy, modifiedDate }

        fetch("https://localhost:7207/Device", {
            method: 'PUT',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(device)
        }).then(() => {
            console.log('Novo Tipo de Dispositivo Criado!')
            history.push("/gerirDevices")
        })

    }

    function GetDeviceTypes() {

        fetch("https://localhost:7207/DeviceType")
            .then((res) => res.json())
            .then((data) => {
                setDeviceTypes(data)
                console.log(data)
            });
    }

    useEffect(() => {
        GetDeviceTypes();
    }, []);


    return (

        <div className="form-group">
            <Link to="/gerirDevices">
                <button>Voltar</button>
            </Link>
            <h2>Criar um Novo Dispositivo</h2>
            <form onSubmit={handleSubmit}>
                <label>Tipo de Dispositivo </label>
                <br></br>

                <select required onChange={(e) => setIdDeviceType(e.target.value)}>
                    {(deviceTypes.items)?.map(deviceType => (
                        <option key={deviceType.id} value={deviceType.id}>{deviceType.name}</option>
                    ))}
                </select>

                <br></br>
                <label>Nome do Dispositivo</label>
                <input
                    placeholder="Nome"
                    class="form-control"
                    type="text"
                    required
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />

                <label>Descrição do Dispositivo</label>
                <textarea
                    placeholder="Descrição"
                    class="form-control"
                    required
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                ></textarea>
                <label>Data de Fabrico</label>
                <td>
                    <input
                        class="form-control"
                        type="datetime-local"
                        required
                        value={manufacturedDate}
                        onChange={(e) => setManufacturedDate(e.target.value)}
                    >
                    </input>
                </td>
                <label>Data da Última Manutenção</label>
                <td>
                    <input
                        class="form-control"
                        type="datetime-local"
                        required
                        value={lastMaintenanceDate}
                        onChange={(e) => setLastMaintenanceDate(e.target.value)}
                    >
                    </input>
                </td>
                <label>Data da Próxima Manutenção</label>
                <td>
                    <input
                        class="form-control"
                        type="datetime-local"
                        required
                        value={maintenanceDueDate}
                        onChange={(e) => setMaintenanceDueDate(e.target.value)}
                    >
                    </input>
                </td>
                <label>Fabricado Por</label>
                <td>
                    <input
                        class="form-control"
                        type="text"
                        required
                        value={manufacturedBy}
                        onChange={(e) => setManufacturedBy(e.target.value)}
                    >
                    </input>
                </td>
                <label>Criado Por</label>
                <td>
                    <input
                        class="form-control"
                        type="text"
                        required
                        value={createdBy}
                        onChange={(e) => setCreatedBy(e.target.value)}
                    >
                    </input>
                </td>
                <label>Data de Produção</label>
                <td>
                    <input
                        class="form-control"
                        type="datetime-local"
                        required
                        value={createdDate}
                        onChange={(e) => setCreatedDate(e.target.value)}
                    >
                    </input>
                </td>
                <label>Modificado Por</label>
                <td>
                    <input
                        class="form-control"
                        type="text"
                        required
                        value={modifiedBy}
                        onChange={(e) => setModifiedBy(e.target.value)}
                    >
                    </input>
                </td>
                <label>Data da Última Modificação</label>
                <td>
                    <input
                        class="form-control"
                        type="datetime-local"
                        required
                        value={modifiedDate}
                        onChange={(e) => setModifiedDate(e.target.value)}
                    >
                    </input>
                </td>
                <button>Adicionar Novo Dispositivo</button>
            </form>
        </div>


    )
}


export default CreateDevice;