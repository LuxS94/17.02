import 'bootstrap/dist/css/bootstrap.min.css';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import Form from 'react-bootstrap/Form';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useNavigate } from "react-router-dom";

function MyFormR() {
   const [form,setform]=useState({username:'',email:'',password:''});
const navigate = useNavigate();
  const change=(e)=>   setform({...form,[e.target.name]: e.target.value})
  const signIn=(e)=>{
    e.preventDefault();
  const url= 'http://localhost:3001/auth/register'
  fetch(url,{
    method:'POST',
    headers:{'Content-Type':'application/json'},
    body:JSON.stringify(form)
  })
  .then(res=>{if(res.ok){return res.json()} else {throw new Error ("Errore nella res")}})
  .then(()=>{setform({ username: '', email: '', password: '' });
    navigate("/login")})
  .catch(err=>console.log(err))
} 
 


  return (
    <div className='d-flex justify-content-center '>
    <Card style={{ width: '18rem' , backgroundColor: 'white' , marginTop: '100px'}}>
       <Form onSubmit={signIn}  className='m-3'>
      <Form.Group className="mb-3" controlId="formBasicUsername">
        <Form.Label>Scegli un username</Form.Label>
        <Form.Control onChange={change} name='username' type="text" placeholder="awesome32" />
      </Form.Group>
<Form.Group className="mb-3" controlId="formBasicEmail">
        <Form.Label>Inserisci la tua email</Form.Label>
        <Form.Control onChange={change} name='email' type="email" placeholder="example@mail.com" />
      </Form.Group>
      <Form.Group className="mb-3" controlId="formBasicPassword">
        <Form.Label>Scegli la tua password</Form.Label>
        <Form.Control onChange={change} name='password' type="password" placeholder="Password" />
         <Form.Text className="text-muted">
          La password deve contenere almeno 8 caratteri, una lettera maiuscola, una minuscola ,un numero ed un carattere speciale.
        </Form.Text>
      </Form.Group>
       <Form.Label>Hai già un account? <Link to="/login">Accedi qui</Link></Form.Label>
      <Button variant="primary" type="submit">
      Registrati
      </Button>
    </Form>
    </Card>
    </div>
  );
}
export default MyFormR;