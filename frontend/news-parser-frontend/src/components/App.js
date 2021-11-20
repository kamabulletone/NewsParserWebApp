import React from "react"
// import Header from "./Header"
// import { Router, Switch } from "react-router";
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import AppNavbar from "./AppNavbar";
import HeaderComponent from "./HeaderComponent"
import NewsColumnContainer from "./NewsColumnContainer"
import NewsTagsContainer from "./NewsTagsContainer";
import AboutComponent from "./AboutComponent";
// import { Router, Switch } from "react-router";
import HomeComponent from "./HomeComponent";

class App extends React.Component {
    state = {
        news: []
       };

    async componentDidMount() {
    const response = await fetch('/api/v1/news/');
    const body = await response.json();
    this.setState({news: body});
    }
    
  render() {

    // <Router>
    //   <Switch>
    //     <Route path="/api/v1/news" exact={true} component={NewsList}/>
    //     <Route path="/api/v1/news" exact={true} component={NewsList}/>
    //   </Switch>
    // </Router> 
      
    const {news} = this.state;
    return (
      <BrowserRouter>
        <Switch>
          <Route path="/about" exact={true} component={AboutComponent}/>
          <Route path="/" exact={true} component={HomeComponent}/>
        </Switch>
      </BrowserRouter>
        // <div className="App">
        //   <AppNavbar/>
        //   <HeaderComponent/>
        //   <NewsTagsContainer/>
        //   <NewsColumnContainer/>
        // </div>
    );
  }
}
export default App