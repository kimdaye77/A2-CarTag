import { BrowserRouter } from 'react-router-dom';
import NavBar from './components/layout/NavBar';
import { CustomRoutes } from './components/router/CustomRoutes';

function App() {
  return (
    <BrowserRouter>
      <NavBar />
      <CustomRoutes />
    </BrowserRouter>
  );
}

export default App;
