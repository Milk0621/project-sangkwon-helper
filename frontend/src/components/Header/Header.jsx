import { Navbar, Nav, Container } from 'react-bootstrap';
import { Link } from 'react-router-dom';

function Header() {
    return(
        <Navbar bg="light" data-bs-theme="light">
            <Container>
            <Navbar.Brand as={Link} to="/">Navbar</Navbar.Brand>
            <Nav className="me-auto">
                <Nav.Link as={Link} to="/">홈</Nav.Link>
                <Nav.Link as={Link} to="/analysis">상권분석</Nav.Link>
                <Nav.Link as={Link} to="/login">로그인</Nav.Link>
            </Nav>
            </Container>
        </Navbar>
    )
}

export default Header;