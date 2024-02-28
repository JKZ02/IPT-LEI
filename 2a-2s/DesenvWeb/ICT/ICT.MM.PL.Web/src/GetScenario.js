import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

function Scenarios() {

    const [scenarios, setScenarios] = useState([]);
    const [firstRun, setFirstRun] = useState(true);

    function GetScenarios() {
        fetch("https://localhost:7207/Scenario")
            .then((res) => res.json())
            .then((data) => {
                setScenarios(data)
                setFirstRun(false)
                console.log(data)
            });
    }

    function eliminarScenario(id) {
        const scenario = { id }
        fetch("https://localhost:7207/Scenario", {
            method: 'DELETE',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(scenario)
        }).then(() => {
            console.log('Cenário Eliminado!')
            GetScenarios()
        })
    }

    useEffect(() => {
        GetScenarios();
    }, []);

    return (
        <div>
            <a href="https://localhost:7207/swagger/index.html" target="_blank">
                <button type="button">Swagger</button>
            </a>
            <Link to="/criarScenario">
                <button>Criar Novo Scenario</button>
            </Link>
            <table className="table table-striped">
                <tbody>
                    <tr>
                        <th>Id</th>
                        <th>Nome</th>
                        <th>Descricao</th>
                    </tr>
                    {(scenarios.items)?.map(scenario => (
                        <tr key={scenario.id}>
                            <td>{scenario.id}</td>
                            <td>{scenario.name}</td>
                            <td>{scenario.description}</td>
                            <td><Link to={{
                                pathname: "/editarScenario",
                                state: { scenario }
                            }} class="btn btn-info" role="button">Editar</Link></td>
                            <td><button class="btn btn-info" onClick={() => eliminarScenario(scenario.id)}>Eliminar</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>)
}

export default Scenarios;