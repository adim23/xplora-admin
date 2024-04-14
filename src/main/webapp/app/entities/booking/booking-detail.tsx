import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './booking.reducer';

export const BookingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bookingEntity = useAppSelector(state => state.booking.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bookingDetailsHeading">
          <Translate contentKey="xploraAdminApp.booking.detail.title">Booking</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.id}</dd>
          <dt>
            <span id="bookDatetime">
              <Translate contentKey="xploraAdminApp.booking.bookDatetime">Book Datetime</Translate>
            </span>
          </dt>
          <dd>
            {bookingEntity.bookDatetime ? (
              <TextFormat value={bookingEntity.bookDatetime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="noPersons">
              <Translate contentKey="xploraAdminApp.booking.noPersons">No Persons</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.noPersons}</dd>
          <dt>
            <span id="noKids">
              <Translate contentKey="xploraAdminApp.booking.noKids">No Kids</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.noKids}</dd>
          <dt>
            <span id="noPets">
              <Translate contentKey="xploraAdminApp.booking.noPets">No Pets</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.noPets}</dd>
          <dt>
            <span id="total">
              <Translate contentKey="xploraAdminApp.booking.total">Total</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.total}</dd>
          <dt>
            <span id="paymentType">
              <Translate contentKey="xploraAdminApp.booking.paymentType">Payment Type</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.paymentType}</dd>
          <dt>
            <span id="valid">
              <Translate contentKey="xploraAdminApp.booking.valid">Valid</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.valid ? 'true' : 'false'}</dd>
          <dt>
            <span id="cancelledAt">
              <Translate contentKey="xploraAdminApp.booking.cancelledAt">Cancelled At</Translate>
            </span>
          </dt>
          <dd>
            {bookingEntity.cancelledAt ? <TextFormat value={bookingEntity.cancelledAt} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="remoteData">
              <Translate contentKey="xploraAdminApp.booking.remoteData">Remote Data</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.remoteData}</dd>
          <dt>
            <span id="remoteId">
              <Translate contentKey="xploraAdminApp.booking.remoteId">Remote Id</Translate>
            </span>
          </dt>
          <dd>{bookingEntity.remoteId}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="xploraAdminApp.booking.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingEntity.createdDate ? <TextFormat value={bookingEntity.createdDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="xploraAdminApp.booking.schedule">Schedule</Translate>
          </dt>
          <dd>{bookingEntity.schedule ? bookingEntity.schedule.code : ''}</dd>
          <dt>
            <Translate contentKey="xploraAdminApp.booking.passenger">Passenger</Translate>
          </dt>
          <dd>{bookingEntity.passenger ? bookingEntity.passenger.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/booking" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/booking/${bookingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BookingDetail;
