import { logDOM } from "@testing-library/react"
import React from "react"
import logo from "../pictures/mirea/logo.png" 

const Footer = () => 
<footer className="page-footer font-small text-white bg-primary pt-4 " >
    <div className="container-fluid text-center text-md-left">

        <div className="row">
            <div className="col-md-6 mt-md-0 mt-3">
                <h5 className="text-uppercase"> <img src={logo} alt="no logo" width="10%"></img> </h5>
                <p>Курсовая работа по дисциплине РСЧИР</p>
            </div>

            <hr className="clearfix w-100 d-md-none pb-0"/>

            <div className="col-md-3 mb-md-0 mb-3">
                <h5 className="text-uppercase">Links</h5>
                <ul className="list-unstyled">
                    <li><a href="https://vk.com/kono_kama_da" style={{color:"white"}}>vk</a></li>
                </ul>
            </div>

            <div className="col-md-3 mb-md-0 mb-3">
                <h5 className="text-uppercase">Links</h5>
                <ul className="list-unstyled">
                    <li><a href="https://github.com/kamabulletone/NewsParserWebApp" style={{color:"white"}}>github</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div className="footer-copyright text-center py-2">© 2020 Copyright
    </div>

</footer>

export default Footer