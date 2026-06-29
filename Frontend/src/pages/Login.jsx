import { useState } from 'react'
import { api } from '../api/client'
import { Link, useNavigate } from 'react-router-dom'

export default function Login() {
  const navigate = useNavigate()
  const [form, setForm] = useState({ name: '', password: '' })
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const onChange = (e) => {
    const { name, value } = e.target
    setForm((p) => ({ ...p, [name]: value }))
  }

  const onSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)
    try {
      await api.login({ name: form.name, password: form.password })
      navigate('/dashboard', { replace: true })
    } catch (err) {
      setError(err?.message || 'Login failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="portal">
      <h1>Student Portal - Login</h1>
      <p className="subtle">Sign in to access your dashboard</p>

      <form className="card" onSubmit={onSubmit}>
        {error && <div className="alert alert-error">{error}</div>}

        <label className="field">
          <span>Name / Username</span>
          <input name="name" value={form.name} onChange={onChange} required placeholder="e.g. sagar" />
        </label>

        <label className="field">
          <span>Password</span>
          <input name="password" type="password" value={form.password} onChange={onChange} required />
        </label>

        <button className="btn" disabled={loading} type="submit">
          {loading ? 'Signing in...' : 'Login'}
        </button>

        <div className="muted">
          New here? <Link to="/register">Register</Link>
        </div>
      </form>
    </div>
  )
}

