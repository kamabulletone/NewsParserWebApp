import React, {Component} from 'react';
import {Navbar, NavbarBrand, Collapse,NavItem, Nav, NavbarToggler, NavLink} from 'reactstrap';
import {Link} from 'react-router-dom';

const AppNavbar = () => {
  return (
    <Navbar color="dark" dark expand="md" className="container-fluid">
        <NavbarBrand href="/">
            newswebparser
        </NavbarBrand>
        <NavbarToggler onClick={function noRefCheck(){}} />
    <Collapse navbar>
        <Nav
        className="me-auto"
        navbar
        >
            <NavItem>
            <NavLink href="/about">
                О нас
            </NavLink>
            </NavItem>
            <NavItem>
            <NavLink href="https://github.com/kamabulletone/NewsParserWebApp" target="_blank">
                GitHub
            </NavLink>
            </NavItem>
        </Nav>
    </Collapse>
    </Navbar>
    
  )
}

export default AppNavbar