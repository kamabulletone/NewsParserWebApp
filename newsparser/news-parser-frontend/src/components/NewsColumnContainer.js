import React, {Component} from 'react';
import {Navbar, NavbarBrand, Collapse,NavItem, Nav, NavbarToggler, NavLink} from 'reactstrap';
import {Link} from 'react-router-dom';
import NewsItemContainer from "./NewsItemContainer"
import "../styles/Footer.css"


const NewsColumnContainer = () => {

    const mystyle = {
        color: "white",
        backgroundColor: "dark",
        padding: "10px",
        fontFamily: "Arial"
      };

      const newsDisplayCount = 3; //How many news to show
      const news = []
        for (var i = 0; i < newsDisplayCount; i++) {
        news.push(<NewsItemContainer/>)
    }

  return (

    <div className="container-fluid main-body">
        <div className="row justify-content-around pb-4">
            {news}
        </div>
        
    </div>
    
  )
}
export default NewsColumnContainer