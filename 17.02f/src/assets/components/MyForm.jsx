import 'bootstrap/dist/css/bootstrap.min.css';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Form from 'react-bootstrap/Form';
import { Link} from 'react-router-dom';
import MyFormR from './MyFormR';
import { useState } from 'react';



function MyForm() {
  const [form,setform]=useState({username:'',mail:'',password:''});

  const change=(e)=>   setform({...form,[e.target.name]: e.target.value})
  const logIn=()=>{
  const url= 'http://localhost:3001/auth/login'
  const token= localStorage.getItem('token')
  fetch(url,{
    method:'POST',
    headers:{'Authorization': `Bearer ${token}`, 'Content-Type':'application/json'},
    body:JSON.stringify(form)
  })
  .then(res=>{if(res.ok){return res.json()} else {throw new Error ("Errore nella res")}})
  .then(data=>console.log(data))
  .catch(err=>console.log(err))
} 
const click= (e)=>e.preventDefault();logIn();
  return (
    <div className='d-flex justify-content-center '>
    <Card style={{ width: '18rem' , backgroundColor: 'white' , marginTop: '100px'}}>
       <Form className='m-3'>
      <Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Inserisci la tua email</Form.Label>
        <Form.Control onChange={change}  name='mail' type="email" placeholder="example@mail.com" />
      </Form.Group>

      <Form.Group className="mb-3" controlId="formBasicPassword">
        <Form.Label>Inserisci password</Form.Label>
        <Form.Control onChange={change} name='password' type="password" placeholder="Password" />
      </Form.Group>
      <Form.Label>Non hai un account? <Link to="/signUp">Registrati</Link></Form.Label>
      <Button onClick={()=>{click();setform({email:'',password:''})}} variant="primary" type="submit">
       Accedi
      </Button>
    </Form>
    </Card>
    </div>
  );
}

export default MyForm;