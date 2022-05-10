import React from "react"
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import AboutComponent from "./AboutComponent";
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
    const {news} = this.state;
    return (
      <BrowserRouter>
        <Switch>
          <Route path="/about" exact={true} component={AboutComponent}/>
          <Route path="/" exact={true} component={HomeComponent}/>
        </Switch>
      </BrowserRouter>
    );
  }
}
export default App