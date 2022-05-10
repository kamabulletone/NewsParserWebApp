import React from "react";
import TodoItem from "./TodoItem";
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class NewsList extends React.Component {
  
  constructor(props) {
    super(props);
    this.state = {news: []};
    this.remove = this.remove.bind(this);
}

componentDidMount() {
    fetch('/api/v1/news/')
        .then(response => response.json())
        .then(data => this.setState({news: data}));
}
  render() {
    return (
      <ul>
        {this.props.todos.map(todo => (
          <TodoItem key={todo.id} todo={todo} />
        ))}
      </ul>
    )
  }
}
export default NewsList