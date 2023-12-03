import reactLogo from '../assets/react.svg'
import viteLogo from '/vite.svg'
import { Typography } from "@mui/material"

export default function Home (){
    
    return(
        <>
            <div>
              <Typography>Ciudadano Consciente</Typography>
            <a href="https://vitejs.dev" target="_blank" rel="noreferrer">
            <img src={viteLogo} className="logo" alt="Vite logo" />
            </a>
            <a href="https://react.dev" target="_blank" rel="noreferrer">
            <img src={reactLogo} className="logo react" alt="React logo" />
            </a>
            </div>
            <h1>Vite + React</h1>
            <p className="read-the-docs">
            Click on the Vite and React logos to learn more
            </p>
        </>

    )
}