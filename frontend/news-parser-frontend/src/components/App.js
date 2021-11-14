import React from "react"
import TodosList from "./NewsList";
// import Header from "./Header"
// import { Router, Switch } from "react-router";
import NewsList from "./NewsList";
// import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import AppNavbar from "./AppNavbar";
import HeaderComponent from "./HeaderComponent"
import NewsColumnContainer from "./NewsColumnContainer"
import NewsTagsContainer from "./NewsTagsContainer";

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
        <div className="App">

          <AppNavbar/>
          <HeaderComponent/>
          <NewsTagsContainer/>
          <NewsColumnContainer/>
          {/* <header className="App-header">
            
            <div className="App-intro">             
              {news.map(report =>
                  <div key={report.id}>
                      <div className="title">
                            <h2>{report.tag}</h2>
                            <a href={report.contentLink}>{report.title}</a>
                            <img src={report.pictures[0].src} className="App-logo" alt="logo" />
                      </div>
                    {report.createdOn}
                  </div>
              )}
            </div>
          </header> */}
        </div>
    );
    // return (
    //     <div>
    //         <Header />
    //         <TodosList todos={this.state.todos} />
    //     </div>
    //   );
  }
}
export default App