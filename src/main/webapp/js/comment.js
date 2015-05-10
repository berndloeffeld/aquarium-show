// Your code here

var Comment = React.createClass({
	render: function() {
		var rawMarkup = marked(this.props.children.toString(), {sanitize: true});
		return (
			<div className="comment">
				<h2 className="commentAuthor">{this.props.author}</h2>
				<span dangerouslySetInnerHTML={{__html: rawMarkup}} />
			</div>
		);
	}
});

var CommentList = React.createClass({
	render: function() {
		var commentNodes = this.props.data.map(function (comment) {
			return (
				<Comment author={comment.author}>
				{comment.text}
				</Comment>
			);
	    });
		
	    return (
	    	<div className="commentList">
	    	{commentNodes}
	    	</div>
	    );
	  }
});

var CommentForm = React.createClass({
	handleSubmit: function(e) {
		e.preventDefault();
	    var author = React.findDOMNode(this.refs.author).value.trim();
	    var text = React.findDOMNode(this.refs.text).value.trim();
	    if (!text || !author) {
	    	return;
	    }
	    
	    this.props.onCommentSubmit({author: author, text: text});
	    
	    React.findDOMNode(this.refs.author).value = '';
	    React.findDOMNode(this.refs.text).value = '';
	    return;
	},
	
	render: function() {
		return (
				<form className="commentForm" onSubmit={this.handleSubmit}>
					<input type="text" placeholder="Your name" ref="author" />
					<input type="text" placeholder="Say something..." ref="text" />
					<input type="submit" value="Post" />
				</form>
			);
		}
	});

var CommentBox = React.createClass({
	loadCommentsFromServer: function() {
		$.ajax({
			url: this.props.url,
			dataType: 'json',
			cache: false,
			success: function(data) {
				this.setState({data: data});
			}.bind(this),
			error: function(xhr, status, err) {
				console.error(data);
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	
	getInitialState: function() {
		return {data: []};
	},

	componentDidMount: function() {
		this.loadCommentsFromServer();
		setInterval(this.loadCommentsFromServer, 10000000);
	},	

	handleCommentSubmit: function(comment) {
	    var comments = this.state.data;
	    var newComments = comments.concat([comment]);
	    this.setState({data: newComments});
	    $.ajax({
	        url: this.props.url,
	        dataType: 'json',
	        contentType: "application/json; charset=utf-8",
	        type: 'POST',
	        data: JSON.stringify(comment),
	        success: function(data) {
	          console.log("Erfolg" + data);
	          this.loadCommentsFromServer;
	          
	        }.bind(this),
	        error: function(xhr, status, err) {
	          console.error(this.props.url, status, err);
	        }.bind(this)
	    });
	},
	
	render: function() {
		return (
			<div className="commentBox">
				<h1>Comments</h1>
				<CommentList data={this.state.data} />
				<CommentForm onCommentSubmit={this.handleCommentSubmit} />
			</div>
		);
	}
});

React.render(
	<CommentBox url="/api/v1/comment/" />,
	document.getElementById('content')
);

