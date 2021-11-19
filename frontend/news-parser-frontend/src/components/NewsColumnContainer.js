import React, {Component} from 'react';
import {Navbar, NavbarBrand, Collapse,NavItem, Nav, NavbarToggler, NavLink} from 'reactstrap';
import {Link} from 'react-router-dom';
import NewsItemContainer from "./NewsItemContainer"


const NewsColumnContainer = () => {

    const mystyle = {
        color: "white",
        backgroundColor: "dark",
        padding: "10px",
        fontFamily: "Arial"
      };

      const newsDisplayCount = 4; //How many news to show
      const news = []
        for (var i = 0; i < newsDisplayCount; i++) {
        news.push(<NewsItemContainer/>)
    }

  return (

    <div className="container-fluid">
        <div className="row justify-content-around">
            {news}
        </div>
        
    </div>
    
  )
}
export default NewsColumnContainer