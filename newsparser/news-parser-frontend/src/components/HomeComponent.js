import React from "react"
import AppNavbar from "./AppNavbar";
import HeaderComponent from "./HeaderComponent"
import NewsColumnContainer from "./NewsColumnContainer"
import NewsTagsContainer from "./NewsTagsContainer";
import AboutComponent from "./AboutComponent";
import { Router, Switch } from "react-router";
import Footer from "./Footer";

class HomeComponent extends React.Component {
    
  render() {
    return (
        <div className="Home">
          <AppNavbar/>
          <HeaderComponent/>
          <NewsTagsContainer/>
          <NewsColumnContainer/>
          <Footer/>
        </div>
    );
  }
}
export default HomeComponent