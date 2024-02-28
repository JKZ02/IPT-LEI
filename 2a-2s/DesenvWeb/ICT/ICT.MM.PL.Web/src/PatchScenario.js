import { useState } from "react";
import { Link } from 'react-router-dom';
import { useLocation } from "react-router-dom";

const EditScenario = () => {

    const location = useLocation()
    const data = location.state

    const [name, setName] = useState(data.scenario.name)
    const [description, setDescription] = useState(data.scenario.description)


    function atualizarScenario(id) {
        const scenarioData = { id, name, description }
        fetch("https://localhost:7207/Scenario", {
            method: 'PATCH',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(scenarioData)
        }).then(() => {
            console.log('Cenario Atualizado!')
            console.log(scenarioData)
        })
    }

    return (
        <div>
            <a href="https://localhost:7207/swagger/index.html" target="_blank">
                <button type="button">Swagger</button>
            </a>

            <table className="table table-striped">
                <tbody>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Descrição</th>
                    </tr>
                    <tr key={data.scenario.id}>
                        <td>
                            {data.scenario.id}
                        </td>
                        <td>
                            <input
                                placeholder={data.scenario.name}
                                class="form-control"
                                type="text"
                                required
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                            >
                            </input>
                        </td>
                        <td>
                            <input
                                placeholder={data.scenario.description}
                                class="form-control"
                                type="text"
                                required
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                            >
                            </input>
                        </td>
                        <td>
                            <Link to="/gerirScenarios" class="btn btn-info" role="button">Cancelar</Link>
                        </td>
                        <td>
                            <Link to="/gerirScenarios" class="btn btn-info" role="button" onClick={() => { atualizarScenario(data.scenario.id) }}>Guardar</Link>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>

    )
}


export default EditScenario;