import React, { useState, useEffect } from 'react';
import AppNavbar from './AppNavbar';
import "../styles/AboutCss.css";
import myPic from "../pictures/aboutPictures/me.jpg"

const AboutComponent = () => {

  return (
    <div>
            <AppNavbar/>
        <div className="about-section">
            <h1>О нас</h1>
            <p>Данный курсовый проект предназначен для повышения навыков разработки в бэкенд и фронтенд сферах.</p>
        </div>
        
        <h2 styles="text-align:center">Команда</h2>
        <div className="row ">
            <div className="column col-lg-3">
                <div className="card">                  
                    <div className="container">
                    <img src={myPic} alt="Tinley" style={{width: "100%"}}></img>
                        <h2>Тинлей Львов</h2>
                        <p className="title">Backend & frontend developer</p>
                        <p>
                            <ul>
                                <li>Образование: Студент 3го курса</li>
                                <li>Специальность: программная инженерия</li>
                            </ul>
                        </p>
                        <p>kamabulletone@yandex.ru</p>
                        <p><button className="button"><a href="https://vk.com/kono_kama_da" target="_blank" >Contact</a></button></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
  )
}
export default AboutComponent;