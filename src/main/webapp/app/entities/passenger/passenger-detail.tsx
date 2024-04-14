import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './passenger.reducer';

export const PassengerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const passengerEntity = useAppSelector(state => state.passenger.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="passengerDetailsHeading">
          <Translate contentKey="xploraAdminApp.passenger.detail.title">Passenger</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="xploraAdminApp.passenger.name">Name</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.name}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="xploraAdminApp.passenger.email">Email</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.email}</dd>
          <dt>
            <span id="mobile">
              <Translate contentKey="xploraAdminApp.passenger.mobile">Mobile</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.mobile}</dd>
          <dt>
            <span id="age">
              <Translate contentKey="xploraAdminApp.passenger.age">Age</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.age}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="xploraAdminApp.passenger.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.gender}</dd>
          <dt>
            <span id="nationality">
              <Translate contentKey="xploraAdminApp.passenger.nationality">Nationality</Translate>
            </span>
          </dt>
          <dd>{passengerEntity.nationality}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.passenger.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {passengerEntity.createdDate ? <TextFormat value={passengerEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/passenger" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/passenger/${passengerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PassengerDetail;
