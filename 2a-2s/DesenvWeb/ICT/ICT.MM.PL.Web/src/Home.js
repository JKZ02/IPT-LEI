import { Link } from 'react-router-dom';

const Home = () => {
return (
    <div className="navbar navbar-inverse">
        <p class="center">
            <table width='100%' className="table table-striped">
                    <tbody>
                            <tr>
                                <td align='center' className="table-primary">
                                    <Link to="/gerirDevices" class="btn btn-primary" role="button">Devices</Link>
                                </td>
                                <td align='center' className="table-success">
                                    <Link to="/gerirDeviceTypes" class="btn btn-success" role="button">DeviceTypes</Link>
                                </td>
                                <td align='center' className="table-danger">
                                    <Link to="/gerirScenarios" class="btn btn-danger" role="button">Scenarios</Link>
                                </td>
                                <td align='center' className="table-warning">
                                    <Link to="/gerirScenarioDevices" class="btn btn-warning" role="button">ScenarioDevices</Link>
                                </td>
                            </tr>
                    </tbody>
            </table>
        </p>
    </div>
)

}

export default Home;