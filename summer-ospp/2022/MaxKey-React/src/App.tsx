import React from 'react';
import './App.scss';
//import Login from "./routes/Login"
import {useNavigate} from "react-router-dom"
function App() {
  let navigate = useNavigate()
  setTimeout(()=>{
    navigate("passport/login",{replace:true})
  },2500)
  return (
     <>
  
     </>
  )
}
export default App;