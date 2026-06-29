import { useMemo, useState } from 'react'
import { api } from '../api/client'
import { Link, useNavigate } from 'react-router-dom'

export default function Register() {
  const navigate = useNavigate()

  const departments = useMemo(
    () => [
      { id: 1, label: 'Department 1' },
      { id: 2, label: 'Department 2' },
      { id: 3, label: 'Department 3' },
      { id: 4, label: 'Department 4' },
    ],
    []
  )

  const [form, setForm] = useState({
    name: '',
    email: '',
    phone: '',
    password: '',
    department_id: 1,
  })

  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

  const onChange = (e) => {
    const { name, value } = e.target
    setForm((p) => ({
      ...p,
      [name]: name === 'department_id' ? Number(value) : value,
    }))
  }

  const onSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setSuccess('')
    setLoading(true)

    try {
      // Backend expects at least: name, password, department_id.
      // We also send email/phone as UI collects them.
      const payload = {
        name: form.name,
        password: form.password,
        department_id: form.department_id,
        email: form.email || null,
        phone: form.phone || null,
      }

      const res = await api.register(payload)
      setSuccess(res?.message || 'Saved successfully')
      navigate('/login', { replace: true })
    } catch (err) {
      setError(err?.message || 'Registration failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="portal">
      <h1>Student Portal - Register</h1>
      <p className="subtle">Create your account</p>

      <form className="card" onSubmit={onSubmit}>
        {error && <div className="alert alert-error">{error}</div>}
        {success && <div className="alert alert-success">{success}</div>}

        <label className="field">
          <span>Name / Username</span>
          <input
            name="name"
            value={form.name}
            onChange={onChange}
            required
            placeholder="e.g. sagar"
          />
        </label>

        <label className="field">
          <span>Email</span>
          <input
            name="email"
            type="email"
            value={form.email}
            onChange={onChange}
            placeholder="you@example.com"
          />
        </label>

        <label className="field">
          <span>Phone</span>
          <input
            name="phone"
            value={form.phone}
            onChange={onChange}
            placeholder="e.g. 9999999999"
          />
        </label>

        <label className="field">
          <span>Password</span>
          <input
            name="password"
            type="password"
            value={form.password}
            onChange={onChange}
            required
          />
        </label>

        <label className="field">
          <span>Department</span>
          <select
            name="department_id"
            value={form.department_id}
            onChange={onChange}
            required
          >
            {departments.map((d) => (
              <option key={d.id} value={d.id}>
                {d.label} (ID: {d.id})
              </option>
            ))}
          </select>
        </label>

        <button className="btn" disabled={loading} type="submit">
          {loading ? 'Registering...' : 'Register'}
        </button>

        <div className="muted">
          Already have an account? <Link to="/login">Login</Link>
        </div>
      </form>
    </div>
  )
}

