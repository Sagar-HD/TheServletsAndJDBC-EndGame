import { Link } from 'react-router-dom'

export default function NotFound() {
  return (
    <div className="portal">
      <h1>404</h1>
      <p className="subtle">Page not found</p>
      <Link to="/login">Go to Login</Link>
    </div>
  )
}

