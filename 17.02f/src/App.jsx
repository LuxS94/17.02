import { BrowserRouter,Routes,Route} from 'react-router-dom'
import './App.css'
import MyForm from './assets/components/MyForm'
import MyFormR from './assets/components/MyFormR'
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {


  return (
    <>
    <BrowserRouter>
    <Routes>
      <Route path="/login" element={<MyForm/>} />
       <Route path="/signUp" element={<MyFormR/>} />
    </Routes>
    </BrowserRouter>
      
      
    </>
  )
}

export default App
