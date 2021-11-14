import React, {Component} from 'react';
import {Navbar, NavbarBrand, Collapse,NavItem, Nav, NavbarToggler, NavLink} from 'reactstrap';
import {Link} from 'react-router-dom';
import TagFormComponent from './TagFormComponent'

const NewsColumnContainer = () => {

    const mystyle = {
        color: "white",
        backgroundColor: "dark",
        padding: "10px",
        fontFamily: "Arial"
      };


  return (

    <div className="container-fluid">
        <div className="row">
            <div className="col-xs-1 col-sm-2 col-md-4 col-lg-3">
                <TagFormComponent/>
                amogus
            </div>
            <div className="col-xs-1 col-sm-2 col-md-4 col-lg-3">
            <TagFormComponent/>
                amogus
            </div >
            <div className="col-xs-1 col-sm-2 col-md-4 col-lg-3">
            <TagFormComponent/>
                amogus
            </div>
            <div className="col-xs-1 col-sm-2 col-md-4 col-lg-3">
            <TagFormComponent/>
                amogus
            </div>
        </div>
        
    </div>
    
  )
}
export default NewsColumnContainer